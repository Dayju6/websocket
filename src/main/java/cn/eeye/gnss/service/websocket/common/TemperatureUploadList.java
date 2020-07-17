/*******************************************************************************
 *  Copyright (c) 2002, 2015 E-EYE High Tech.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package cn.eeye.gnss.service.websocket.common;

import cn.eeye.common.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 多路温度上传数据列表
 *
 * @author KwanKin Yau
 *
 */
public class TemperatureUploadList {

	/**
	 * 温度值输出格式
	 */
	static final DecimalFormat FORMAT = new DecimalFormat("0.#");

	private List<TemperatureUploadData> list = new ArrayList<TemperatureUploadData>();

	/**
	 *
	 */
	public TemperatureUploadList() {
	}

	/**
	 * 添加一路温度上传数据
	 *
	 * @param sensorId
	 *            传感器ID
	 * @param state
	 *            传感器状态字
	 * @param tempValue
	 *            温度值
	 * @param humidity
	 *            湿度值
	 * @return 温度上传数据对象
	 */
	public TemperatureUploadData add(int sensorId, int state, Float tempValue,
			Float humidity) {
		TemperatureUploadData data = new TemperatureUploadData(sensorId, state,
				tempValue, humidity);
		list.add(data);
		return data;
	}

	/**
	 * 转换成平台协议的参数值字符串。
	 *
	 * @see //GnssDeviceCmdConsts.LocationReportParams.MULTI_TEMP_DATA
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(32);

		for (TemperatureUploadData data : list) {
			if (str.length() > 0)
				str.append('|');

			str.append(data.getSensorId());
			str.append(',');
			str.append(data.getState());
			str.append(',');
			Float tempValue = data.getTempValue();
			if (tempValue != null)
				str.append(FORMAT.format(tempValue.floatValue()));

			Float humidity = data.getHumidity();
			if (humidity != null) {
				str.append(',');
				str.append(FORMAT.format(humidity.floatValue()));
			}
		}

		return str.toString();
	}

	/**
	 * 从字符串中解释多路温度上传数据
	 *
	 * @param s
	 *            字符串
	 * @return 是否成功解释数据。当解释失败时，本对象的数据的内容不可用。
	 *
	 */
	public boolean parseFromString(String s) {
		list.clear();

		String[] arr = s.split("\\|");

		for (String piece : arr) {
			String[] elmts = piece.split(",");

			if (elmts.length >= 3) {
				Integer sensorId = Utils.tryStrToInteger(elmts[0]);
				if (sensorId == null)
					return false;

				Integer state = Utils.tryStrToInteger(elmts[1]);
				if (state == null)
					return false;

				Float tempValue;
				String t = elmts[2];
				if (t != null && t.length() > 0) {
					tempValue = Utils.tryStrToFloat(elmts[2]);
					if (tempValue == null)
						return false;
				} else
					tempValue = null;

				Float humidity = null;
				if (elmts.length >= 4)
					humidity = Utils.tryStrToFloat(elmts[3]);

				add(sensorId.intValue(), state.intValue(), tempValue, humidity);
			} else
				return false;
		}

		return list.size() > 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TemperatureUploadList clone() {
		TemperatureUploadList r = new TemperatureUploadList();
		for (TemperatureUploadData data : list)
			r.add(data.getSensorId(), data.getState(), data.getTempValue(),
					data.getHumidity());
		return r;
	}

	/**
	 * 取温度上传数据列表
	 *
	 * @return 温度上传数据列表
	 */
	public List<TemperatureUploadData> getList() {
		return list;
	}

	public Stream<TemperatureUploadData> stream() {
		return list.stream();
	}


	/**
	 * 检查是否有任意正常的温度数据（即没有故障标志的）。
	 *
	 * @return 是否有任意正常的温度数据
	 */
	public boolean hasAnyNormalTempData() {
		for (TemperatureUploadData data : list) {
			if (!data.isSensorFault())
				return true;
		}

		return false;
	}

}
