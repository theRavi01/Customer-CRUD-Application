package com.customercrud.helper;

import lombok.Data;

public @Data class Helper {

	 private  String  search  =  ""  ; 
	 private  String  searchBy  =  ""  ; 
	 private  int  currentPage  = 0; 
	 private  int  itemsPerPage  = 0; 
	 private  String  sortBy  ; 
	 private  String  sortOrder  ;
}