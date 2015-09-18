package neeapaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * PasteContoller
 * Return JSON containing information about the current machine that java can gather.
 */
@RestController
public class PasteController {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private PasteService mPasteService;

    @Autowired
    private UserService mUserService;

    /**
     * Add a new paste via post with data
     * @param aTitle Title of the paste
     * @param aContent Content of the paste
     */
    @RequestMapping(value="/paste" , method = RequestMethod.POST)
    public String postPaste(@RequestParam(value="title", defaultValue = "Untitled") String aTitle,
                            @RequestParam(value="content", defaultValue = "none") String aContent,
                            @RequestParam(value="user", defaultValue = "none") String aUser,
                            @RequestParam(value="pass", defaultValue = "none") String aPass)
    {
        if (!aUser.equals("none")  && !aPass.equals("none")) {
            mUserService.authenticate(aUser,aPass);
        }

        mPasteService.insert(aTitle,aContent);
        // Return url with paste.
        return "http://localhost:8080/paste/" + Long.toString(mPasteService.getLastPasteId()) + "\n";
    }

    /**
     * Search pastes for content(case insensitive)
     * @param aSearchQuery the text to search for
     * @return a list of Pastes containing the search text
     */
    @RequestMapping("/search")
    public List<Paste> searchPastes(@RequestParam(value = "q") String aSearchQuery)
    {
        //TODO - mPastesService.search
        return new ArrayList<>();
    /*
        List<Paste> lReturn = new ArrayList<Paste>();
        for(Paste lPaste : mPastes) {
            // Search both content and title
            if ( (lPaste.getContent() + lPaste.getTitle()).toLowerCase().contains(aSearchQuery.toLowerCase())) {
                lReturn.add(lPaste);
            }
        }
        return lReturn;
        */
    }

    /**
     * Get a paste and it's attributes.
     * If not found, returns a new Paste with an id of -1 and attributes set to 'not found'
     * @param aId The ID of the paste to get
     * @return the Paste object associated with the ID
     */
    @RequestMapping("/paste/{id}")
    public Paste getPaste(@PathVariable(value="id") String aId) {
        // validate id
        int lIndex = Integer.parseInt(aId);
        return mPasteService.getById(lIndex);
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
        return mPasteService.getPropertyById(Long.parseLong(aId), aProperty);
    }

    /**
     * return all pastes loaded
     * @return list of all pastes
     */
    @RequestMapping("/paste/all")
    public List<Map<String,Object>> allPastes()
    {
        return mPasteService.findAllPastes();
    }

    @RequestMapping(value = "/user/create/", method = RequestMethod.POST)
    public String createUser(@RequestParam(value = "user", defaultValue = "none") String aUser,
                             @RequestParam(value = "pass", defaultValue = "none") String aPass)
    {
        if (aUser == "none" || aPass == "none") {
            return "Attach data parameters 'user' and 'pass' to create a new user.";
        }

        if (mUserService.exists(aUser)) {
            return "Username taken.";
        }

        mUserService.insert(aUser,aPass);
        return "User created.";
    }
}