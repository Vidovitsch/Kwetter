package Service;

import DAO.Mock.HashtagDaoMock;
import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IHashtagDao;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;
import Exception.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KweetService {

    private IKweetDao kweetDao;

    private IHashtagDao hashtagDao;

    private IUserDao userDao;

    public KweetService() {
        this.userDao = new UserDaoMock();
        this.kweetDao = new KweetDaoMock(userDao.findAll());
        this.hashtagDao = new HashtagDaoMock(kweetDao.findAll());
    }

    public KweetService(IKweetDao kweetDao, IHashtagDao hashtagDao, IUserDao userDao) {
        this.kweetDao = kweetDao;
        this.hashtagDao = hashtagDao;
        this.userDao = userDao;
    }

    /**
     * To Do
     *
     * @param kweetId
     * @return
     * @throws UserNotFoundException
     */
    public Kweet publish(Long userId, Long kweetId) throws UserNotFoundException, InvalidKweetException {
        return publish(userId, kweetDao.findById(kweetId));
    }

    public Kweet publish(Long userId, Kweet kweet) throws UserNotFoundException, InvalidKweetException {
        // Find user by id and set is as sender of the kweet
        User sender = userDao.findById(userId);
        if (sender == null) {
            throw new UserNotFoundException();
        } else {
            validateKweet(kweet);

            kweet.setSender(sender);

            // Make sure the sender knows of kweet
            if (!sender.getKweets().contains(kweet)) {
                sender.getKweets().add(kweet);
            }

            // Filter message on hashtags '#' and mentions '@' and add to kweet
            addHashtags(kweet, parseNames('#', kweet.getMessage()));
            addMentions(kweet, parseNames('@', kweet.getMessage()));

            if (kweet.getId() == null) {
                // Kweet isn't persisted. Persist new kweet
                return kweetDao.create(kweet);
            } else {
                // Kweet is already persisted. Update existing kweet
                return kweetDao.update(kweet);
            }
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
     * @param userId
     */
    public Kweet giveHeart(Long userId, Long kweetId) throws NullPointerException {
        User user = userDao.findById(userId);
        Kweet kweet = kweetDao.findById(kweetId);
        if (user != null && kweet != null) {
            if (!kweet.getHearts().contains(user)) {
                kweet.getHearts().add(user);

                // Make sure the user knows of kweet
                if (!user.getHearts().contains(kweet)) {
                    user.getHearts().add(kweet);
                }

                kweetDao.update(kweet);
            }
        } else {
            throw new NullPointerException();
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
            if (!hashtag.getKweets().contains(kweet)) {
                hashtag.getKweets().add(kweet);
            }
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
            if (!user.getMentions().contains(kweet)) {
                user.getMentions().add(kweet);
            }
        }
        kweet.setMentions(mentions);
    }

    private void validateKweet(Kweet kweet) throws InvalidKweetException, IllegalArgumentException {
        if (kweet.getMessage() == null || kweet.getMessage().equals("")) {
            throw new InvalidKweetException("Message required");
        } else if (kweet.getMessage().length() > 140) {
            throw new InvalidKweetException("Message should have a maximum of 140 characters");
        }
    }
}
