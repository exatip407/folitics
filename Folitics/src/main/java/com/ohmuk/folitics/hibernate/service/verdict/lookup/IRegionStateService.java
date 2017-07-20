package com.ohmuk.folitics.hibernate.service.verdict.lookup;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.RegionState;

/**
 * Service interface for {@link RegionState}
 * @author Abhishek
 *
 */
public interface IRegionStateService {

    /**
     * This method finds the region to which the state belongs
     * 
     * @author Abhishek
     * @param java.lang.String state for which region is required
     * @return com.ohmuk.folitics.jpa.entity.verdict.lookup.RegionState the RegionState object found for given state
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public Region getRegionForState(String state) throws MessageException;
}
