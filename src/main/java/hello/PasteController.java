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

    @RequestMapping(value="/paste" , method = RequestMethod.POST)
    public void postPaste(@RequestParam(value="title", defaultValue = "none") String aTitle,
                           @RequestParam(value="content", defaultValue = "none") String aContent)
    {
        mPastes.add(new Paste(counter.getAndIncrement(), aTitle, aContent));
    }

    //consider changing to type list<paste> and returning a length one list when there is only one on return.
    @RequestMapping("/paste/{id}")
    public Object getPaste(@PathVariable(value="id") String aId,
                           @RequestParam(value = "query", defaultValue = "") String aSearchQuery) {
        switch(aId)
        {
            case "all":
                return mPastes;
            case "search":
                // search and return all pastes with text matching query(assume + for space)
                if(aSearchQuery != "") {
                    List<Paste> lReturn = new ArrayList<Paste>();
                    for(Paste lPaste : mPastes) {
                        // Search both content and title
                        if ( (lPaste.getContent() + lPaste.getTitle()).contains(aSearchQuery)) {
                            lReturn.add(lPaste);
                        }
                    }
                    return lReturn;
                }
                else {
                    //Fall through to not found result.
                    aId = "-1";
                }

            default:
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

    }
}