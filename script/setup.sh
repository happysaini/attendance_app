#!/usr/bin/env bash

# Install AVD files
echo "y" | $ANDROID_HOME/tools/bin/sdkmanager --install 'system-images;android-27;google_apis;x86'

# Create emulator
echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n nexus -k 'system-images;android-27;google_apis;x86' --force

# Create Second emulator
echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n google -k 'system-images;android-27;google_apis;x86' --force

$ANDROID_HOME/emulator/emulator -list-avds

echo "Starting emulator"

# Start emulator in background
nohup $ANDROID_HOME/emulator/emulator -avd nexus -port 5554 -no-boot-anim -no-snapshot > /dev/null 2>&1 &
$ANDROID_HOME/platform-tools/adb -s emulator-5554 wait-for-device shell 'while [[ -z $(getprop sys.boot_completed | tr -d '\r') ]]; do sleep 1; done; input keyevent 82'

nohup $ANDROID_HOME/emulator/emulator -avd google -port 5556 -no-boot-anim -no-snapshot > /dev/null 2>&1 &
$ANDROID_HOME/platform-tools/adb -s emulator-5556 wait-for-device shell 'while [[ -z $(getprop sys.boot_completed | tr -d '\r') ]]; do sleep 1; done; input keyevent 82'


$ANDROID_HOME/platform-tools/adb devices
export PATH=$PATH:$ANDROID_HOME/platform-tools/

echo "Emulator started"