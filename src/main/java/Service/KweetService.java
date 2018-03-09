package Service;

import DaoInterfaces.IHashtagDao;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;
import Exception.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
public class KweetService {

    @EJB
    private IKweetDao kweetDao;

    @EJB
    private IHashtagDao hashtagDao;

    @EJB
    private IUserDao userDao;

    public KweetService() { }

    /**
     * To Do
     *
     * @param kweet
     * @return
     * @throws UserNotFoundException
     */
    public void publish(Kweet kweet) throws UserNotFoundException, InvalidKweetException {
        validateKweet(kweet);

        // Filter message on hashtags '#' and mentions '@' and add to kweet
        addHashtags(kweet, parseNames('#', kweet.getMessage()));
        addMentions(kweet, parseNames('@', kweet.getMessage()));

        if (kweet.getId() == null) {
            // Kweet isn't persisted. Persist new kweet
            kweetDao.create(kweet);
        } else {
            // Kweet is already persisted. Update existing kweet
            kweetDao.update(kweet);
        }
    }

    /**
     * To Do
     *
     * @param kweet
     * @return
     */
    public boolean delete(Kweet kweet) {
        return kweetDao.remove(kweet);
    }

    /**
     * To Do
     *
     * @param kweet
     * @param user
     */
    public void giveHeart(Kweet kweet, User user) {
        if (!kweet.getHearts().contains(user)) {
            kweet.getHearts().add(user);
            kweetDao.update(kweet);
        }
    }

    /**
     * To Do
     *
     * @param term
     * @return
     */
    public List<Kweet> search(String term) {
        if (term != null && !term.equals("")) {
            List<Kweet> searchResults = new ArrayList<Kweet>();
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
            return new ArrayList<Kweet>();
        }
    }

    private List<String> parseNames(char prefix, String message) {
        Pattern pattern = Pattern.compile("(" + prefix + "\\w+)\\b");
        Matcher matcher = pattern.matcher(message);

        // HashSet to remove duplicates
        Collection<String> names = new HashSet<String>();
        while (matcher.find()) {
            names.add(matcher.group(1));
        }

        return new ArrayList<String>(names);
    }

    private void addHashtags(Kweet kweet, List<String> names) {
        List<Hashtag> hashtags = new ArrayList<Hashtag>();
        for (String name : names) {
            Hashtag hashtag = hashtagDao.findByName(name);
            if (hashtag == null) {
                hashtag = new Hashtag();
                hashtag.setName(name);
            }
            hashtags.add(updateHashtag(kweet, hashtag));
        }
        kweet.setHashtags(hashtags);
    }

    private Hashtag updateHashtag(Kweet kweet, Hashtag hashtag) {
        hashtag.setLastUsed(kweet.getPublicationDate());
        hashtag.setTimesUsed(hashtag.getTimesUsed() + 1);

        return hashtag;
    }

    private void addMentions(Kweet kweet, List<String> names) throws UserNotFoundException {
        List<User> mentions = new ArrayList<User>();
        for (String name : names) {
            User user = userDao.findByUsername(name);
            if (user == null) {
                throw new UserNotFoundException();
            } else {
                mentions.add(user);
            }
        }
        kweet.setMentions(mentions);
    }

    private void validateKweet(Kweet kweet) throws InvalidKweetException, IllegalArgumentException {
        if (kweet.getMessage() == null || kweet.getMessage().equals("") || kweet.getMessage().length() <= 140) {
            throw new InvalidKweetException("Message required");
        } else if (kweet.getMessage().length() <= 140) {
            throw new InvalidKweetException("Message should have a maximum of 140 characters");
        } else if (kweet.getSender() == null) {
            throw new IllegalArgumentException();
        }
    }
}
