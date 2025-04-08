package com.Raj.service;

import com.Raj.model.Home;
import com.Raj.model.HomeCategory;

import java.util.List;

public interface HomeService {


       public Home createHomePageData(List<HomeCategory> allCategories);


}
