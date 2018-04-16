package services;

import dao.interfaces.IHashtagDao;
import dao.interfaces.IKweetDao;
import dao.interfaces.IProfileDao;
import dao.interfaces.IUserDao;
import domain.Hashtag;
import domain.Kweet;
import domain.User;
import exceptions.*;
import util.KweetConverter;
import viewmodels.JsfKweet;
import viewmodels.TimelineItem;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named(value = "kweetService")
@Stateless
public class KweetService {

    @Inject
    private IKweetDao kweetDao;

    @Inject
    private IHashtagDao hashtagDao;

    @Inject
    private IUserDao userDao;

    @Inject
    private IProfileDao profileDao;

    public void setKweetDao(IKweetDao kweetDao) {
        this.kweetDao = kweetDao;
    }

    public void setHashtagDao(IHashtagDao hashtagDao) {
        this.hashtagDao = hashtagDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setProfileDao(IProfileDao profileDao) {this.profileDao = profileDao;}

    /**
     * Updates an existing/persisted kweet.
     *
     * @param username of the sender of the kweet
     * @param kweetId of the kweet to be updated
     * @param message that is updated
     * @return the updated version of the kweet
     * @throws KweetNotFoundException when the kweetId isn't equal to any of the persisted id's
     * @throws UserNotFoundException when mentions in the message aren't equal to any of the persisted usernames
     * @throws InvalidKweetException when the updated message has null, empty or a too long value (max. 140 characters)
     */
    public Kweet update(String username, Long kweetId, String message) throws KweetNotFoundException,
            UserNotFoundException, InvalidKweetException {
        Kweet kweet = kweetDao.findById(kweetId);
        if (kweet != null && kweet.getSender().getUsername().equals(username)) {
            validateMessage(message);
            kweet.setMessage(message);

            // Filter message on hashtags '#' and mentions '@' and add to kweet
            addHashtags(kweet, parseNames('#', kweet.getMessage()));
            addMentions(kweet, parseNames('@', kweet.getMessage()));

            return kweetDao.update(kweet);
        } else {
            throw new KweetNotFoundException();
        }
    }

    /**
     * Creates a new kweet.
     *
     * @param username of the sender of the new kweet
     * @param message to be send in the kweet
     * @return the new persisted kweet
     * @throws InvalidKweetException when the updated message has null, empty or a too long value (max. 140 characters)
     * @throws UserNotFoundException when mentions in the message or usernamearen't equal to any of the persisted usernames
     */
    public Kweet create(String username, String message) throws InvalidKweetException, UserNotFoundException {
        try {
            User sender = userDao.findByUsername(username);
            if (sender != null) {
                validateMessage(message);

                Kweet kweet = new Kweet();
                kweet.setMessage(message);
                kweet.setSender(sender);

                // Filter message on hashtags '#' and mentions '@' and add to kweet
                addHashtags(kweet, parseNames('#', kweet.getMessage()));
                addMentions(kweet, parseNames('@', kweet.getMessage()));

                Kweet k = kweetDao.create(kweet);

                // Make sure the user knows of kweet
                syncWithKweets(kweetDao.findBySender(sender), k);

                return k;
            } else {
                throw new UserNotFoundException();
            }
        } catch (Exception e) {
            try {
                throw (EJBException) new EJBException(e).initCause(e);
            } catch (IllegalStateException stateException) {
                throw e;
            }
        }
    }

    /**
     * Deletes a persisted kweet.
     *
     * @param kweetId of the kweet to be deleted
     * @return true if the kweet existed and has been deleted, else false
     */
    public boolean delete(Long kweetId) {
        Kweet kweet = kweetDao.findById(kweetId);
        return kweetDao.remove(kweet);
    }

    /**
     * Gives a heart ('like') to a persisted kweet.
     *
     * @param username of the user that gives a heart
     * @param kweetId of the kweet that gets a heart
     * @return the updated kweet that got a heart
     * @throws UserNotFoundException when the username isn't equal to any peristed usernames
     * @throws KweetNotFoundException when the kweetId isn't equal to any persisted kweets
     */
    public Kweet giveHeart(String username, Long kweetId) throws UserNotFoundException, KweetNotFoundException {
        User user = userDao.findByUsername(username);
        Kweet kweet = kweetDao.findById(kweetId);
        if (user != null && kweet != null) {
            if (!kweet.getHearts().contains(user)) {
                kweet.getHearts().add(user);

                // Make sure the user knows of kweet
                syncWithKweets(user.getHearts(), kweet);

                kweetDao.update(kweet);
            }
        } else if (user == null) {
            throw new UserNotFoundException();
        } else {
            throw new KweetNotFoundException();
        }

        return kweet;
    }

    /**
     * Search within all persisted kweet by the given search term.
     * @param term to search kweets. The term will be used to search for hashtags or senders.
     *             It's a valid search result when a part of the hashtag or sender is equal to the given term.
     * @return list of found kweets where the term equals parts the of hashtags or senders
     */
    public List<TimelineItem> search(String term) {
        List<Kweet> kweetResults = new ArrayList<>();
        if (term != null && !term.equals("")) {
            for (Kweet kweet : kweetDao.findAll()) {
                addSearchResultsOfSender(term, kweet, kweetResults);
                addSearchResultsOfHashtag(term, kweet, kweetResults);
            }
        }
        List<TimelineItem> searchResults = new ArrayList<>();
        for (Kweet kweet : kweetResults) {
            searchResults.add(KweetConverter.toTimelineItem(kweet, false, profileDao));
        }
        return searchResults;
    }

    private List<String> parseNames(char prefix, String message) {
        Pattern pattern = Pattern.compile("(" + prefix + "\\w+)\\b");
        Matcher matcher = pattern.matcher(message);

        // HashSet to remove duplicates
        Collection<String> names = new HashSet<>();
        while (matcher.find()) {
            names.add(matcher.group(1).substring(1));
        }

        return new ArrayList<>(names);
    }

    private void addHashtags(Kweet kweet, List<String> names) {
        List<Hashtag> hashtags = new ArrayList<>();
        for (String name : names) {
            Hashtag hashtag = hashtagDao.findByName(name);
            if (hashtag == null) {
                hashtag = new Hashtag();
                hashtag.setName(name);
                hashtagDao.create(hashtag);
            }
            hashtags.add(updateHashtag(kweet, hashtag));

            // Make sure the hashtag knows of kweet
            syncWithKweets(hashtag.getKweets(), kweet);
        }
        kweet.setHashtags(hashtags);
    }

    private Hashtag updateHashtag(Kweet kweet, Hashtag hashtag) {
        hashtag.setLastUsed(kweet.getPublicationDate());
        hashtag.setTimesUsed(hashtag.getTimesUsed() + 1);

        return hashtag;
    }

    private void addMentions(Kweet kweet, List<String> names) throws UserNotFoundException {
        List<User> mentions = new ArrayList<>();
        for (String name : names) {
            User user = userDao.findByUsername(name);
            if (user == null) {
                throw new UserNotFoundException();
            } else {
                mentions.add(user);
            }

            // Make sure the mentioned user knows of kweet
            syncWithKweets(user.getMentions(), kweet);
        }
        kweet.setMentions(mentions);
    }

    private void validateMessage(String message) throws InvalidKweetException {
        if (message == null || message.equals("")) {
            throw new InvalidKweetException("Message required");
        } else if (message.length() > 140) {
            throw new InvalidKweetException("Message should have a maximum of 140 characters");
        }
    }

    private void syncWithKweets(List<Kweet> userKweets, Kweet kweet) {
        if (!userKweets.contains(kweet)) {
            userKweets.add(kweet);
        }
    }

    private void addSearchResultsOfSender(String term, Kweet kweet, List<Kweet> searchResults) {
        if (kweet.getSender().getUsername().toLowerCase().contains(term.toLowerCase())) {
            searchResults.add(kweet);
        }
    }

    private void addSearchResultsOfHashtag(String term, Kweet kweet, List<Kweet> searchResults) {
        for (Hashtag hashtag : kweet.getHashtags()) {
            if (hashtag.getName().toLowerCase().contains(term.toLowerCase())) {
                searchResults.add(kweet);
                break;
            }
        }
    }

    public boolean deleteKweet(long kweetId){
        try {
            kweetDao.remove(kweetDao.findById(kweetId));
            return true;
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    public List<TimelineItem> allKweets(){
        List<TimelineItem> searchResults = new ArrayList<>();
        for (Kweet kweet : kweetDao.findAll()) {
            searchResults.add(KweetConverter.toTimelineItem(kweet, false, profileDao));
        }
        return searchResults;
    }

    public List<JsfKweet> JsfKweets(){
        List<JsfKweet> searchResults = new ArrayList<>();
        for (Kweet kweet : kweetDao.findAll()) {
            searchResults.add(new JsfKweet( kweet.getId(),kweet.getPublicationDate(), kweet.getSender().getUsername(), kweet.getMessage()));
        }
        return searchResults;
    }

    public List<JsfKweet> JsfKweets(String filter){
        List<JsfKweet> searchResults = new ArrayList<>();
        for (Kweet kweet : kweetDao.findAll()) {
            if(kweet.getMessage().toLowerCase().contains(filter.toLowerCase())){
            searchResults.add(new JsfKweet(kweet.getId(), kweet.getPublicationDate(), kweet.getSender().getUsername(), kweet.getMessage()));}
        }
        return searchResults;
    }
}
