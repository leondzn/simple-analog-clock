/*
 * Copyright (C) 2019 Leonard Dizon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leondzn.simpleanalogclock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;

public class SimpleAnalogClock extends RelativeLayout {
  private final AppCompatImageView face;
  private final AppCompatImageView hour;
  private final AppCompatImageView minute;
  private final AppCompatImageView second;

  private static final long HOURS = 3600000L;
  private static final long MINUTES = 60000L;
  private static final long SECONDS = 1000L;

  public SimpleAnalogClock(Context context) {
    this(context, null);
  }

  public SimpleAnalogClock(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SimpleAnalogClock(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }


  @SuppressWarnings("WeakerAccess")
  public SimpleAnalogClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);

    inflate(context, R.layout.analog_clock, this);

    face = findViewById(R.id.face);
    hour = findViewById(R.id.hour_hand);
    minute = findViewById(R.id.minute_hand);
    second = findViewById(R.id.second_hand);

    TypedArray typedArray = context.getTheme().obtainStyledAttributes(
        attrs, R.styleable.SimpleAnalogClock, defStyleAttr, defStyleRes);

    Drawable faceDrawable = typedArray.getDrawable(R.styleable.SimpleAnalogClock_faceDrawable);
    Drawable hourDrawable = typedArray.getDrawable(R.styleable.SimpleAnalogClock_hourDrawable);
    Drawable minuteDrawable = typedArray.getDrawable(R.styleable.SimpleAnalogClock_minuteDrawable);
    Drawable secondDrawable = typedArray.getDrawable(R.styleable.SimpleAnalogClock_secondDrawable);

    setFaceDrawable(faceDrawable != null ? faceDrawable : context.getDrawable(R.drawable.clock_00_face))
        .setHourDrawable(hourDrawable != null ? hourDrawable : context.getDrawable(R.drawable.clock_00_short))
        .setMinuteDrawable(minuteDrawable != null ? minuteDrawable : context.getDrawable(R.drawable.clock_00_long))
        .setSecondDrawable(secondDrawable != null ? secondDrawable : context.getDrawable(R.drawable.clock_00_second));

    int faceColor = typedArray.getColor(R.styleable.SimpleAnalogClock_faceTint, -1);
    int hourColor = typedArray.getColor(R.styleable.SimpleAnalogClock_hourTint, -1);
    int minuteColor = typedArray.getColor(R.styleable.SimpleAnalogClock_minuteTint, -1);
    int secondColor = typedArray.getColor(R.styleable.SimpleAnalogClock_secondTint, -1);
    if (faceColor != -1) setFaceTint(faceColor);
    if (hourColor != -1) setHourTint(hourColor);
    if (minuteColor != -1) setMinuteTint(minuteColor);
    if (secondColor != -1) setSecondTint(secondColor);

    rotateHourHand(typedArray.getFloat(R.styleable.SimpleAnalogClock_hourRotation, 0));
    rotateMinuteHand(typedArray.getFloat(R.styleable.SimpleAnalogClock_minuteRotation, 0));
    rotateSecondHand(typedArray.getFloat(R.styleable.SimpleAnalogClock_secondRotation, 0));
  }

  @SuppressWarnings("WeakerAccess")
  public SimpleAnalogClock setFaceDrawable(Drawable drawable) {
    face.setImageDrawable(drawable);
    return this;
  }

  @SuppressWarnings("WeakerAccess")
  public SimpleAnalogClock setHourDrawable(Drawable drawable) {
    hour.setImageDrawable(drawable);
    return this;
  }

  @SuppressWarnings("WeakerAccess")
  public SimpleAnalogClock setMinuteDrawable(Drawable drawable) {
    minute.setImageDrawable(drawable);
    return this;
  }

  @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
  public SimpleAnalogClock setSecondDrawable(Drawable drawable) {
    second.setImageDrawable(drawable);
    return this;
  }

  @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
  public SimpleAnalogClock rotateHourHand(float angle) {
    hour.setRotation(angle);
    return this;
  }

  @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
  public SimpleAnalogClock rotateMinuteHand(float angle) {
    minute.setRotation(angle);
    return this;
  }

  @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
  public SimpleAnalogClock rotateSecondHand(float angle) {
    second.setRotation(angle);
    return this;
  }

  @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
  public SimpleAnalogClock setTime(int hour, int min, int seconds, int millis) {
    long hourMillis = hour * HOURS;
    long minMillis = min * MINUTES;
    long secondMillis = seconds * SECONDS;

    rotateHourHand((float) 0.0000083 * (hourMillis + minMillis + secondMillis + millis));
    rotateMinuteHand((float) 0.0001 * (minMillis + secondMillis + millis));
    rotateSecondHand((float) 0.006 * (secondMillis + millis));
    return this;
  }

  @SuppressWarnings({"WeakerAccess", "unused"})
  public SimpleAnalogClock setTime(int hour, int min, int seconds) {
    return setTime(hour, min, seconds, 0);
  }

  @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
  public SimpleAnalogClock setFaceTint(int color) {
    face.setColorFilter(color);
    return this;
  }

  @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
  public SimpleAnalogClock setHourTint(int color) {
    hour.setColorFilter(color);
    return this;
  }

  @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
  public SimpleAnalogClock setMinuteTint(int color) {
    minute.setColorFilter(color);
    return this;
  }

  @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
  public SimpleAnalogClock setSecondTint(int color) {
    second.setColorFilter(color);
    return this;
  }
}
