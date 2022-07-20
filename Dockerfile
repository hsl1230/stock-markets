FROM openjdk:latest
# Set the time zone for the container
ENV TZ=Canada/Mountain
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ARG work_dir="stock-markets"
RUN mkdir $work_dir

# run under a user. This makes the whole thing more secure
# RUN groupadd normalgroup
# RUN useradd -G normalgroup normaluser
# RUN chown normaluser:normalgroup $work_dir
# USER normaluser:normalgroup

WORKDIR /${work_dir}

# Method 1: it will not copy the source code, and you have to do a clean build before create the image
ARG JAR_FILE=./build/libs/*SNAPSHOT.jar
# COPY ${JAR_FILE} ./app.jar
# ENTRYPOINT ["java","-jar","./app.jar"]

# Method 2: it will copy the source code, and run through bootRun
COPY . ./
# ENTRYPOINT ["./gradlew","bootRun"]

# Method 3: it will copy the source code, and run a clean build, then start by run the jar
RUN ./gradlew clean build
RUN cp ${JAR_FILE} ./app.jar
ENTRYPOINT ["java","-jar","./app.jar"]

EXPOSE 8080
