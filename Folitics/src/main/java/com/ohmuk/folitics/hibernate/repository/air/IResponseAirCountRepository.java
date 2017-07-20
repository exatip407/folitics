package com.ohmuk.folitics.hibernate.repository.air;

import com.ohmuk.folitics.hibernate.entity.air.ResponseAirCount;
import com.ohmuk.folitics.hibernate.entity.air.ResponseAirCountId;

public interface IResponseAirCountRepository extends
		IAirCountHibernateRepository<ResponseAirCount> {

	public ResponseAirCount find(ResponseAirCountId id);

	public void delete(ResponseAirCountId id);

}
