package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.Statelookup;

public interface IStatelookupService {

	public List<Statelookup> search(String state) throws Exception;

}
