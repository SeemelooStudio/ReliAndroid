package com.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StationListItem implements Parcelable{

	private int stationId;
	
	private String stationName;
	
	private String type;
	
	private String eastOrWest;

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String strStationName) {
		this.stationName = strStationName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEastOrWest() {
		return eastOrWest;
	}

	public void setEastOrWest(String eastOrWest) {
		this.eastOrWest = eastOrWest;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(stationId);
		out.writeString(stationName);
		out.writeString(type);
		out.writeString(eastOrWest);
	}

	public static final Parcelable.Creator<StationListItem> CREATOR = new Parcelable.Creator<StationListItem>() {
		public StationListItem createFromParcel(Parcel in) {
		    return new StationListItem(in);
		}
		public StationListItem[] newArray(int size) {
		    return new StationListItem[size];
		}
	};
		
	private StationListItem(Parcel in) {
		stationId = in.readInt();
		stationName = in.readString();
		type = in.readString();
		eastOrWest = in.readString();
	}
	
	public StationListItem()
	{
	}
}
