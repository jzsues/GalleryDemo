/*
 * Best Principles & Sample Code.
 * GalleryDemo ImageSource.java
 * jiangzm 2012-7-31
 * All rights reserved.
 */
package com.jiangzm.adapter;

/**
 * @author jiangzm
 * 
 */
public class ImageSource {
	public static final int PAGE_SIZE = 18;

	private String[] imageUrls = {
			"http://p1.kaixin001.com/privacy/photo/97/2/90971019_860970256_show.jpg?578dc0fd4ff04d0e745e73cde9828d52",
			"http://p1.kaixin001.com/privacy/photo/97/3/90971019_860970333_show.jpg?2aaab1a84d55e6221fdb4c1d931d1898",
			"http://p1.kaixin001.com/privacy/photo/97/4/90971019_860970407_show.jpg?ede4870fe026d52c9d865c9d884c0f6c",
			"http://p1.kaixin001.com/privacy/photo/97/4/90971019_860970478_show.jpg?eab82d6c2707573d10d5b1f26201f1b6",
			"http://p1.kaixin001.com/privacy/photo/97/5/90971019_860970536_show.jpg?d96d9446b31f420183ddbf0ea9d2ee53",
			"http://p1.kaixin001.com/privacy/photo/97/6/90971019_860970667_show.jpg?d226890064cb465edb768d6aebfe0353",
			"http://p1.kaixin001.com/privacy/photo/97/7/90971019_860970717_show.jpg?8b94285ddc24d58e93f4d1aa928d79fe",
			"http://p1.kaixin001.com/privacy/photo/97/8/90971019_860970806_show.jpg?674468c34155fbd825e6bda50d7f9879",
			"http://p1.kaixin001.com/privacy/photo/97/8/90971019_860970875_show.jpg?ff82fd63f940fade9b6ebec219fdb292",
			"http://p1.kaixin001.com/privacy/photo/97/9/90971019_860970956_show.jpg?73bd4c74e4cf65bc281201135b3a3e86",
			"http://p1.kaixin001.com/privacy/photo/97/10/90971019_860971020_show.jpg?d2be8b1add57b724b9bf9161f916768e",
			"http://p1.kaixin001.com/privacy/photo/79/18/90971019_860791825_show.jpg?7f70eae1360ccd28dac29c180838a36b",
			"http://p1.kaixin001.com/privacy/photo/79/18/90971019_860791879_show.jpg?dbe9504da8da4b56e8d1914bf0bcd8a6",
			"http://p1.kaixin001.com/privacy/photo/78/98/90971019_860789828_show.jpg?791b75c8211e3ad732388f057bcad1ef",
			"http://p1.kaixin001.com/privacy/photo/78/98/90971019_860789838_show.jpg?c423a13737a71fdbe1b99f1a92af3cae",
			"http://p1.kaixin001.com/privacy/photo/78/98/90971019_860789850_show.jpg?717d9da9fcef1b479826f156457c8605",
			"http://p1.kaixin001.com/privacy/photo/78/98/90971019_860789857_show.jpg?ce98c5c21856260ba13ccdd4f66418e1",
			"http://p1.kaixin001.com/privacy/photo/78/98/90971019_860789862_show.jpg?4f837b2f62432f4db2560517dc35fed1",
			"http://p1.kaixin001.com/privacy/photo/79/0/90971019_860790007_show.jpg?e5a44c4edade51394154044e14faafc3",
			"http://p1.kaixin001.com/privacy/photo/79/0/90971019_860790013_show.jpg?97bb3ebde1793cf851c3923b48a1d89c",
			"http://p1.kaixin001.com/privacy/photo/79/1/90971019_860790192_show.jpg?b801f86b5a6d66098174ee8edf5a1dc6",
			"http://p1.kaixin001.com/privacy/photo/79/2/90971019_860790272_show.jpg?5b4fed04624927ad1aec28d5de7f043d",
			"http://p1.kaixin001.com/privacy/photo/78/87/90971019_860788777_show.jpg?9a5ae14750481e9440a0b4bac7bc6f47",
			"http://p1.kaixin001.com/privacy/photo/78/82/90971019_860788248_show.jpg?c454763f6b74b8919cba71e6fc96702f",
			"http://p1.kaixin001.com/privacy/photo/78/78/90971019_860787845_show.jpg?408de35a191e192d8b37380ca5cf9c52",
			"http://p1.kaixin001.com/privacy/photo/78/75/90971019_860787594_show.jpg?bc101e79f3796915e43ce91f04301396",
			"http://p1.kaixin001.com/privacy/photo/78/74/90971019_860787412_show.jpg?2ca9a44cd8f7ca8bef735fd3caf29f0c",
			"http://p1.kaixin001.com/privacy/photo/10/10/90971019_847101009_show.jpg?33a823b3b16acc35a6a03b763319c2fa",
			"http://p1.kaixin001.com/privacy/photo/10/78/90971019_824107875_show.jpg?ab8fbb1c7c1891f78fca8432fd2c1548",
			"http://p1.kaixin001.com/privacy/photo/10/68/90971019_824106854_show.jpg?430255697932bc2aa8d86f961d9e105a",
			"http://p1.kaixin001.com/privacy/photo/27/75/90971019_758277556_show.jpg?7fc47f756d6bde7c8ac82af88f7cab3e" };

	private static ImageSource instance;

	private ImageSource() {
	}

	public static ImageSource getInstance() {
		if (instance == null) {
			instance = new ImageSource();
		}
		return instance;
	}

	public int getTotalPage() {
		int length = getCount();
		int mod = length % PAGE_SIZE;
		if (mod == 0) {
			return length / PAGE_SIZE;
		} else {
			return (length / PAGE_SIZE) + 1;
		}
	}

	public int getCount() {
		return imageUrls.length;
	}

	/**
	 * base 0
	 * 
	 * @param page
	 * @return
	 */
	public int getCount(int page) {
		int count = (page + 1) * PAGE_SIZE;
		int length = getCount();
		if (count > length) {
			return length;
		}
		return count;
	}

	public String getItem(int position) {
		if (position > -1 && position < getCount()) {
			return imageUrls[position];
		}
		return null;
	}
}
