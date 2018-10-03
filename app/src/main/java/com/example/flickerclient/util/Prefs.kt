package com.example.flickerclient.util

import android.content.Context

object Prefs {

    /**
     * Set a string shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    fun setString(context: Context, key: String, value: String) {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        val editor = settings.edit()
        editor.putString(key, value)
        editor.apply()
    }


    /**
     * Set a integer shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    fun setInt(context: Context, key: String, value: Int) {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        val editor = settings.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * Set a float shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    fun setFloat(context: Context, key: String, value: Float) {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        val editor = settings.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    /**
     * Set a double shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    fun setDouble(context: Context, key: String, value: Double) {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        val editor = settings.edit()
        editor.putString(key, value.toString())
        editor.apply()
    }

    /**
     * Set a long shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    fun setLong(context: Context, key: String, value: Long) {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        val editor = settings.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * Set a Boolean shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    fun setBoolean(context: Context, key: String, value: Boolean) {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        val editor = settings.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * Get a string shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getString(context: Context, key: String, defValue: String): String {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        return settings.getString(key, defValue)
    }

    /**
     * Get a integer shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getInt(context: Context, key: String, defValue: Int): Int {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        return settings.getInt(key, defValue)
    }

    /**
     * Get a float shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getFloat(context: Context, key: String, defValue: Float): Float {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        return settings.getFloat(key, defValue)
    }

    /**
     * Get a double shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getDouble(context: Context, key: String, defValue: Double): Double {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        val stringValue = settings.getString(key, defValue.toString())
        return java.lang.Double.parseDouble(stringValue!!)
    }

    /**
     * Get a long shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getLong(context: Context, key: String, defValue: Long): Long {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        return settings.getLong(key, defValue)
    }

    /**
     * Get a boolean shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getBoolean(context: Context, key: String, defValue: Boolean): Boolean {
        val defaultFileName = context.packageName + "_preferences"
        val settings = context.getSharedPreferences(defaultFileName, 0)
        return settings.getBoolean(key, defValue)
    }
}