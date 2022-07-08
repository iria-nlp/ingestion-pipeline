#!/bin/bash

JAR=$(find . -name *assembly*.jar)

echo "removing signature files from the following jar: $JAR"
echo ""
zip -d $JAR 'META-INF/.SF' 'META-INF/.RSA' 'META-INF/*SF'
