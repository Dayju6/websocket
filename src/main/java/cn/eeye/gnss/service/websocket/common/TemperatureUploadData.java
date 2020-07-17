/*******************************************************************************
 *  Copyright (c) 2002, 2015 E-EYE High Tech.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package cn.eeye.gnss.service.websocket.common;

import cn.eeye.common.Consts;
import cn.eeye.common.utils.Utils;

/**
 * 温度上传数据
 *
 * @author KwanKin Yau
 *
 */
public class TemperatureUploadData implements Cloneable {

	/**
	 * 传感器故障
	 */
	public static final int BIT__SENSOR_FAULT = 0;

	/**
	 * 终端高温报警
	 */
	public static final int BIT__DEVICE_HIGH_TEMP_ALARM = 1;

	/**
	 * 终端低温报警
	 */
	public static final int BIT__DEVICE_LOW_TEMP_ALARM = 2;

	/**
	 * 平台高温报警
	 */
	public static final int BIT__PLATFORM_HIGH_TEMP_ALARM = 3;

	/**
	 * 平台低温报警
	 */
	public static final int BIT__PLATFORM_LOW_TEMP_ALARM = 4;

	private int sensorId;
	private int state;
	private Float tempValue;
	private Float humidity;

	/**
	 * @param sensorId
	 * @param state
	 * @param tempValue
	 * @param humidity
	 */
	public TemperatureUploadData(int sensorId, int state, Float tempValue,
			Float humidity) {
		super();
		this.sensorId = sensorId;
		this.state = state;
		this.tempValue = tempValue;
		this.humidity = humidity;
	}

	/**
	 * 取传感器ID
	 *
	 * @return 传感器ID
	 */
	public int getSensorId() {
		return sensorId;
	}

	/**
	 * 取传感器状态字
	 *
	 * @return 传感器状态字
	 */
	public int getState() {
		return state;
	}

	/**
	 * 取温度值(℃)
	 *
	 * @return 温度值
	 */
	public Float getTempValue() {
		return tempValue;
	}

	/**
	 * 取湿度值(%s)，如60.5%则值为60.5
	 *
	 * @return 湿度值
	 */
	public Float getHumidity() {
		return humidity;
	}

	/**
	 * 检测状态位
	 *
	 * @param bitIndex
	 *            位序号
	 * @return 是否置位
	 */
	public boolean stateBitTest(int bitIndex) {
		return Utils.bitTest(state, bitIndex);
	}

	/**
	 * 取是否有传感器故障状态
	 *
	 * @return 是否有传感器故障状态
	 */
	public boolean isSensorFault() {
		return stateBitTest(BIT__SENSOR_FAULT);
	}

	/**
	 * 取是否有终端高温报警状态
	 *
	 * @return 是否有终端高温报警状态
	 */
	public boolean isDeviceHighTempAlarm() {
		return stateBitTest(BIT__DEVICE_HIGH_TEMP_ALARM);
	}

	/**
	 * 取是否有终端低温报警状态
	 *
	 * @return 是否有终端低温报警状态
	 */
	public boolean isDeviceLowTempAlarm() {
		return stateBitTest(BIT__DEVICE_LOW_TEMP_ALARM);
	}

	/**
	 * 取是否有平台高温报警状态
	 *
	 * @return 是否有平台高温报警状态
	 */
	public boolean isPlatformHighTempAlarm() {
		return stateBitTest(BIT__PLATFORM_HIGH_TEMP_ALARM);
	}

	/**
	 * 设置状态位。
	 *
	 * @param bitIndex
	 *            位序号
	 */
	public void stateBitSet(int bitIndex) {
		state = Utils.bitSet(state, bitIndex);
	}

	/**
	 * 设置平台高温报警状态
	 */
	public void setPlatformHighTempAlarm() {
		stateBitSet(BIT__PLATFORM_HIGH_TEMP_ALARM);
	}

	/**
	 * 设置平台低温报警状态
	 */
	public void setPlatformLowTempAlarm() {
		stateBitSet(BIT__PLATFORM_LOW_TEMP_ALARM);
	}

	/**
	 * 取是否有平台低温报警状态
	 *
	 * @return 是否有平台低温报警状态
	 */
	public boolean isPlatformLowTempAlarm() {
		return stateBitTest(BIT__PLATFORM_LOW_TEMP_ALARM);
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @param tempValue
	 *            the tempValue to set
	 */
	public void setTempValue(Float tempValue) {
		this.tempValue = tempValue;
	}

	/**
	 * @param humidity
	 *            the humidity to set
	 */
	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}

	public String temperatureString() {
		if (tempValue != null)
			return TemperatureUploadList.FORMAT.format(tempValue.floatValue());
		else
			return Consts.EMPTY_STRING;
	}

	public String humidityString() {
		if (humidity != null)
			return TemperatureUploadList.FORMAT.format(humidity.floatValue());
		else
			return Consts.EMPTY_STRING;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TemperatureUploadData clone() {
		return new TemperatureUploadData(sensorId, state, tempValue, humidity);
	}

}
