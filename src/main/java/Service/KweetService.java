package Service;

import DaoInterfaces.IHashtagDao;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;
import Exception.*;
import Qualifier.Mock;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named(value = "kweetService")
@Stateless
public class KweetService {

    @Inject @Mock
    private IKweetDao kweetDao;

    @Inject @Mock
    private IHashtagDao hashtagDao;

    @Inject @Mock
    private IUserDao userDao;

    public KweetService() { }

    public void setKweetDao(IKweetDao kweetDao) {
        this.kweetDao = kweetDao;
    }

    public void setHashtagDao(IHashtagDao hashtagDao) {
        this.hashtagDao = hashtagDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    // Method for REST testing!
    public Kweet update(Long userId, Long kweetId, String message) throws KweetNotFoundException,
            UserNotFoundException, InvalidKweetException {
        return update(userDao.findById(userId).getUsername(), kweetId, message);
    }

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

    // Method for REST testing!
    public Kweet create(Long userId, String message) throws UserNotFoundException, InvalidKweetException {
        return create(userDao.findById(userId).getUsername(), message);
    }

    public Kweet create(String username, String message) throws UserNotFoundException, InvalidKweetException {
        // Find user by id and set is as sender of the kweet
        User sender = userDao.findByUsername(username);
        if (sender != null) {
            validateMessage(message);

            Kweet kweet = new Kweet();
            kweet.setMessage(message);
            kweet.setSender(sender);

            // Make sure the sender knows of kweet
            syncWithKweets(sender.getKweets(), kweet);

            // Filter message on hashtags '#' and mentions '@' and add to kweet
            addHashtags(kweet, parseNames('#', kweet.getMessage()));
            addMentions(kweet, parseNames('@', kweet.getMessage()));

            return kweetDao.create(kweet);
        } else {
            throw new UserNotFoundException();
        }
    }

    /**
     * To Do
     *
     * @param kweetId
     * @return
     */
    public boolean delete(Long kweetId) {
        Kweet kweet = kweetDao.findById(kweetId);
        return kweetDao.remove(kweet);
    }

    /**
     * To Do
     *
     * @param kweetId
     * @param username
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
        } else if (user == null){
            throw new UserNotFoundException();
        } else {
            throw new KweetNotFoundException();
        }

        return kweet;
    }

    /**
     * To Do
     *
     * @param term
     * @return
     */
    public List<Kweet> search(String term) {
        if (term != null && !term.equals("")) {
            List<Kweet> searchResults = new ArrayList<>();
            for (Kweet kweet : kweetDao.findAll()) {
                if (kweet.getSender().getUsername().contains(term)) {
                    searchResults.add(kweet);
                } else {
                    for (Hashtag hashtag : kweet.getHashtags()) {
                        if (hashtag.getName().contains(term)) {
                            searchResults.add(kweet);
                            break;
                        }
                    }
                }
            }
            return searchResults;
        } else {
            return new ArrayList<>();
        }
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

    private void validateMessage(String message) throws InvalidKweetException, IllegalArgumentException {
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
}
