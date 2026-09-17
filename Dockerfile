# ==========================================================
# Docker image untuk build APK QR Scanner dari command line,
# tanpa perlu install Android Studio di komputer host.
#
# Cara pakai (lihat juga build.sh):
#   docker build -t qrscanner-builder .
#   docker run --rm -v "$PWD":/app -w /app qrscanner-builder gradle assembleDebug
#
# Hasil APK akan muncul langsung di folder proyek kamu di:
#   app/build/outputs/apk/debug/app-debug.apk
# ==========================================================

FROM eclipse-temurin:17-jdk-jammy

ENV ANDROID_SDK_ROOT=/opt/android-sdk \
    ANDROID_HOME=/opt/android-sdk \
    GRADLE_VERSION=8.9 \
    GRADLE_HOME=/opt/gradle \
    DEBIAN_FRONTEND=noninteractive

ENV PATH="${PATH}:${GRADLE_HOME}/bin:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools"

RUN apt-get update && \
    apt-get install -y --no-install-recommends wget unzip ca-certificates git && \
    rm -rf /var/lib/apt/lists/*

# Install Gradle (samakan dengan versi di gradle/wrapper/gradle-wrapper.properties)
RUN wget -q "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" -O /tmp/gradle.zip && \
    unzip -q /tmp/gradle.zip -d /opt && \
    mv "/opt/gradle-${GRADLE_VERSION}" "${GRADLE_HOME}" && \
    rm /tmp/gradle.zip

# Install Android SDK command-line tools + paket yang dibutuhkan proyek ini
RUN mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools" && \
    wget -q "https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip" -O /tmp/cmdline-tools.zip && \
    unzip -q /tmp/cmdline-tools.zip -d "${ANDROID_SDK_ROOT}/cmdline-tools" && \
    mv "${ANDROID_SDK_ROOT}/cmdline-tools/cmdline-tools" "${ANDROID_SDK_ROOT}/cmdline-tools/latest" && \
    rm /tmp/cmdline-tools.zip

RUN yes | sdkmanager --licenses > /dev/null 2>&1 || true
RUN sdkmanager --install "platform-tools" "platforms;android-34" "build-tools;34.0.0" > /dev/null

WORKDIR /app

# Source proyek dipasang lewat volume mount saat 'docker run' (lihat build.sh),
# bukan disalin ke dalam image, supaya image tetap ringan dan bisa dipakai ulang
# untuk proyek Android lain juga.
CMD ["gradle", "assembleDebug", "--no-daemon"]
