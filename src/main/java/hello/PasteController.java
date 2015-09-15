package hello;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * PasteContoller
 * Return JSON containing information about the current machine that java can gather.
 */
@RestController
public class PasteController {

    private final AtomicLong counter = new AtomicLong();
    //Manage a list of pastes:
    private List<Paste> mPastes;

    public PasteController()
    {
        mPastes = new ArrayList<Paste>();
    }

    /**
     * Add a new paste via post with data
     * @param aTitle Title of the paste
     * @param aContent Content of the paste
     */
    @RequestMapping(value="/paste" , method = RequestMethod.POST)
    public void postPaste(@RequestParam(value="title", defaultValue = "none") String aTitle,
                           @RequestParam(value="content", defaultValue = "none") String aContent)
    {
        mPastes.add(new Paste(counter.getAndIncrement(), aTitle, aContent));
    }

    /**
     * Search pastes for content(case insensitive)
     * @param aSearchQuery the text to search for
     * @return a list of Pastes containing the search text
     */
    @RequestMapping("/search/{query}")
    public List<Paste> searchPastes(@PathVariable(value = "query") String aSearchQuery)
    {
        List<Paste> lReturn = new ArrayList<Paste>();
        for(Paste lPaste : mPastes) {
            // Search both content and title
            if ( (lPaste.getContent() + lPaste.getTitle()).toLowerCase().contains(aSearchQuery.toLowerCase())) {
                lReturn.add(lPaste);
            }
        }
        return lReturn;
}

    /**
     * Get a paste and it's attributes.
     * If not found, returns a new Paste with an id of -1 and attributes set to 'not found'
     * @param aId The ID of the paste to get
     * @return the Paste object associated with the ID
     */
    @RequestMapping("/paste/{id}")
    public Paste getPaste(@PathVariable(value="id") String aId,
                           @RequestParam(value = "query", defaultValue = "") String aSearchQuery) {
        // validate id
        int lIndex = Integer.parseInt(aId);
        if(lIndex < 0 || lIndex > mPastes.size()-1)
        {
            return new Paste(-1, "Not found", "Not found");
        }
        else
        {
            return mPastes.get(lIndex);
        }
    }

    /**
     * return all pastes loaded
     * @return list of all pastes
     */
    @RequestMapping("/pastes/all")
    public List<Paste> allPastes()
    {
        return mPastes;
    }
}