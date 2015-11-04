# CloudAutomatorHTTP
This is Cloud Automator's HTTP Trigger Kicker.

# How to use?
## Write setting.ini
+ Go to Cloud Automator admin web site.
+ Select HTTP Trigger specify.
+ Then copy exec sample like below, and paste in setting.ini file.
```
curl https://manager.cloudautomator.com/trigger/xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx \
  -X POST \
  -H "Authorization: CAAuth xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Content-Length: 0"
```

## Execute CloudAutomatorHTTP
### for Windows
execute CloudAutomatorHTTP.bat
### for Mac
execute CloudAutomatorHTTP.sh

# What is Cloud Automator ?
- in Japanese
 - http://cloudautomator.com/
- in English
 - http://cloudautomator.com/en/