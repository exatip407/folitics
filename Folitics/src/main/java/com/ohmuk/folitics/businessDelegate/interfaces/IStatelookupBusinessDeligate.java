package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.Statelookup;

public interface IStatelookupBusinessDeligate {
	public List<Statelookup> search(String state)throws Exception;
}
