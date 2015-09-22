package neeapaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * PasteContoller
 * Return JSON containing information about the current machine that java can gather.
 */
@RestController
public class PasteController {

    @Autowired
    private PasteRepository mPasteRepo;

    @Autowired
    private UserRepository mUserRepo;


    /**
     * Add a new paste via post with data
     * @param aTitle Title of the paste
     * @param aContent Content of the paste
     */
    @RequestMapping(value="/paste" , method = RequestMethod.POST)
    public String postPaste(@RequestParam(value="title", defaultValue = "Untitled") String aTitle,
                            @RequestParam(value="content", defaultValue = "none") String aContent,
                            @RequestParam(value="user", defaultValue = "none") String aUser,
                            @RequestParam(value="pass", defaultValue = "none") String aPass) {
        if (!aUser.equals("none")  && !aPass.equals("none")) {
            User lUser = mUserRepo.findByUsername(aUser);
            if (lUser.authenticate(aPass)) {
                Paste lPaste = new Paste(aTitle,aContent);
                mPasteRepo.save(lPaste);
                lUser.OwnPaste(mPasteRepo.findOne(mPasteRepo.count()));
                mUserRepo.save(lUser);
                return "http://localhost:8080/paste/" + Long.toString(mPasteRepo.count()) + "\n";
            }
            else {
                return "Authentification failed.";
            }
        }
        else {
            mPasteRepo.save(new Paste(aTitle,aContent));
            return "http://localhost:8080/paste/" + Long.toString(mPasteRepo.count()) + "\n";
        }
    }

    /**
     * Search pastes for content(case insensitive)
     * @param aSearchQuery the text to search for
     * @return a list of Pastes containing the search text
     */
    @RequestMapping("/search")
    public List<Paste> searchPastes(@RequestParam(value = "q", defaultValue = "none") String aSearchQuery) {
        // Search all pastes:
        List<Paste> lMatches = new ArrayList<>();
        for (Paste lPaste : mPasteRepo.findAll()) {
            if ((lPaste.getTitle() + lPaste.getContent()).toLowerCase().contains(aSearchQuery))
            {
                lMatches.add(lPaste);
            }
        }
       return lMatches;
    }

    /**
     * Get a paste and it's attributes.
     * If not found, returns a new Paste with an id of -1 and attributes set to 'not found'
     * @param aId The ID of the paste to get
     * @return the Paste object associated with the ID
     */
    @RequestMapping("/paste/{id}")
    public Paste getPaste(@PathVariable(value="id") String aId) {
        long lIndex = Integer.parseInt(aId);
        return mPasteRepo.findOne(lIndex);
    }

    /**
     * return all pastes belonging to a user.
     * @param aUser
     * @return
     */
    @RequestMapping("/user/{user}")
    public List<Paste> getUserPastes(@PathVariable(value = "user") String aUser) {
        return mUserRepo.findByUsername(aUser).Pastes;
    }

    /**
     * Get the value of a paste property
     * @param aId The ID of the paste to get the property from
     * @param aProperty The property to get
     * @return the String value of the property
     */
    @RequestMapping("/paste/{id}/{property}")
    public String getPasteProperty(@PathVariable(value="id") String aId,
                          @PathVariable(value="property") String aProperty) {
        Long lId = Long.parseLong(aId);
        if ( lId < 1 || lId > mPasteRepo.count()) {
            return "Paste not found.";
        }

        // Return the property
        Paste lPaste = mPasteRepo.findOne(lId);
        aProperty = aProperty.toLowerCase();

        switch(aProperty) {
            case "title": return lPaste.getTitle();
            case "content": lPaste.setViews(lPaste.getViews()+1) ; return lPaste.getContent();
            case "id" : return Long.toString(lPaste.getId()); // heh.
            case "views" : return Long.toString(lPaste.getViews());
            default:
                return "Property " + aProperty + " not found.";
        }
    }

    /**
     * return all pastes loaded
     * @return list of all pastes
     */
    @RequestMapping("/paste/all")
    public List<Paste> allPastes()
    {
        return mPasteRepo.findAll();
    }

    @RequestMapping(value = "/user/create/", method = RequestMethod.POST)
    public String createUser(@RequestParam(value = "user", defaultValue = "none") String aUser,
                             @RequestParam(value = "pass", defaultValue = "none") String aPass) {
        if (aUser.equals("none") || aPass.equals("none")) {
            return "Attach data parameters 'user' and 'pass' to create a new user.";
        }

        /*
        if (mUserRepo.exists()) {
            return "Username taken.";
        }
        */

        mUserRepo.save(new User(aUser, BCrypt.hashpw(aPass, BCrypt.gensalt())));
        return "User created.";
    }
}



