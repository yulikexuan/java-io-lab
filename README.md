# Large File IO


## Commands
- curl -w "@curl_format.txt" -s -v "http://localhost:8080/io/fasted/file/size"
- curl -w "@curl_format.txt" -s -v "http://localhost:8080/io/fasted/file/target/exists"
- curl -w "@curl_format.txt" -s -v -X DELETE "http://localhost:8080/io/fasted/file/target"
- curl -w "@curl_format.txt" -s -v -X PATCH "http://localhost:8080/io/fasted/file/target"