`mvn clean package` 

`java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./heap_dump.hprof  -jar target/heap-error-practise-1.0-SNAPSHOT.jar`

analyze using `https://eclipse.dev/mat/downloads.php`
on mac open by `open /Applications/mat.app`