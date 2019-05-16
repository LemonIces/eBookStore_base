package com.le.ebook.dao;

import com.le.ebook.domain.MallCar;

public interface MallCarDao extends BaseBao{

	Object getOne(String hql,Object[] params);

	void save(MallCar mallCar);

	void update(MallCar existMall);

}
