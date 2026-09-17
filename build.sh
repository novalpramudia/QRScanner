#!/usr/bin/env bash
# ==========================================================
# Script untuk build APK QR Scanner secara otomatis.
#
# Mode 1 (default, TANPA Docker): memakai Gradle Wrapper lokal.
#         Membutuhkan JDK 17 sudah terpasang di komputer.
# Mode 2 (--docker): build di dalam container Docker, sehingga
#         TIDAK perlu install JDK/Android SDK di komputer sendiri.
#         Membutuhkan Docker sudah terpasang & berjalan.
#
# Contoh pemakaian:
#   ./build.sh                # build APK debug pakai gradlew lokal
#   ./build.sh release        # build APK release pakai gradlew lokal
#   ./build.sh --docker       # build APK debug lewat Docker
#   ./build.sh --docker release
# ==========================================================

set -e

BUILD_TYPE="debug"
USE_DOCKER=false

for arg in "$@"; do
  case "$arg" in
    --docker) USE_DOCKER=true ;;
    release)  BUILD_TYPE="release" ;;
    debug)    BUILD_TYPE="debug" ;;
    *)
      echo "Argumen tidak dikenali: $arg"
      echo "Gunakan: ./build.sh [debug|release] [--docker]"
      exit 1
      ;;
  esac
done

TASK="assembleDebug"
if [ "$BUILD_TYPE" = "release" ]; then
  TASK="assembleRelease"
fi

echo ">> Mode Docker : $USE_DOCKER"
echo ">> Tipe build  : $BUILD_TYPE"
echo ">> Gradle task : $TASK"
echo ""

if [ "$USE_DOCKER" = true ]; then
  if ! command -v docker >/dev/null 2>&1; then
    echo "Docker tidak ditemukan. Install Docker terlebih dahulu atau jalankan tanpa --docker."
    exit 1
  fi

  echo ">> Membangun Docker image (qrscanner-builder)..."
  docker build -t qrscanner-builder .

  echo ">> Menjalankan build di dalam container..."
  docker run --rm \
    -v "$(pwd)":/app \
    -w /app \
    qrscanner-builder \
    gradle "$TASK" --no-daemon
else
  if [ ! -f "./gradlew" ]; then
    echo "File gradlew tidak ditemukan. Buka proyek ini sekali lewat Android Studio"
    echo "agar Gradle Wrapper otomatis dibuat, lalu jalankan ulang script ini."
    exit 1
  fi
  chmod +x ./gradlew
  ./gradlew "$TASK"
fi

echo ""
echo ">> Build selesai. Cek hasil APK di:"
echo "   app/build/outputs/apk/${BUILD_TYPE}/"
