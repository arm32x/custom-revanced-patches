#!/usr/bin/env bash

# Build the patches, use them to patch the OverDrive app, and install the result
# to a connected device over ADB.
#
# - Must be run on Linux (for /dev/fd)
# - The OverDrive APK must be located at `work/overdrive.apk`
# - ReVanced CLI must be on PATH

set -euo pipefail
cd "$(dirname "$(dirname "$(readlink -f "$0" || realpath "$0")")")"

# Build the project and get its version at the same time
# This should avoid the overhead of running Gradle twice, but it looks quite strange
patches_version="$(./gradlew :patches:build :patches:printVersion | tee /dev/fd/2 | grep "^Version " | cut -d' ' -f2)"

cd work
revanced-cli patch \
  overdrive.apk \
  --patches="../patches/build/libs/patches-${patches_version}.rvp" \
  --bypass-verification \
  --install