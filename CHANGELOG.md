# Changelog

## 2.4.1

# BEFORE RELEASE: Streamline between Cobblenav and Pokenav and make the N capitalized
# FIX ALL TODO'S BEFORE RELEASING

### Additions

* Added Compatibility with ModMenu
* Added a Config Editing Screen
* Added the Correct Info to fabric.mod.json
* Added MeAlam as Author

### Changes

* Removed Unused Files
* Changed RadarFilterTypes to ResourceLocation in stead of String
    * This allows for better compatibility with other mods that may add their own radar filter types
* Fixed typing any special characters in the pokefinder filterbar causing a crash
* Moved SpawnDataHelper inside ServerStartedEvent since it needs the server to exist

### Fixes

* Fixed concurrent modification during spawn catalogue encoding
* Fixed Pokefinder Properties filter working incorrectly
* Fixed Toasts from leaking through the CobbleNav GUI's
  * All Toasts should render on top of the CobbleNav GUI's

### Developer

* Added Github Templates

### Localization
