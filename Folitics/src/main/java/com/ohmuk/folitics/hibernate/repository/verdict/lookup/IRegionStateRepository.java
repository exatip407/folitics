package com.ohmuk.folitics.hibernate.repository.verdict.lookup;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.RegionState;

/**
 * Hibernate repository interface for entity: {@link RegionState}
 * @author Abhishek
 *
 */
public interface IRegionStateRepository {

    /**
     * This method finds the region to which the state belongs
     * 
     * @author Abhishek
     * @param java.lang.String state for which region is required
     * @return com.ohmuk.folitics.jpa.entity.verdict.lookup.RegionState the RegionState object found for given state
     */
    public RegionState getRegionForState(String state);
}
