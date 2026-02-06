#!/usr/bin/env sh
DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
WRAPPER_JAR="$DIR/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Missing gradle-wrapper.jar (Android Studio zwykle go nie wymaga do importu, ale CLI tak)."
  echo "Zaimportuj projekt w Android Studio (File > Open)."
  exit 1
fi

exec java -jar "$WRAPPER_JAR" "$@"
