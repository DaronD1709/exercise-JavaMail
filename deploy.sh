#!/bin/bash
mvn clean package
WAR_FILE=$(find target -maxdepth 1 -name "*.war")
cp "$WAR_FILE" /opt/homebrew/Cellar/tomcat@10/10.1.57/libexec/webapps/
echo "Đã deploy: $WAR_FILE"
