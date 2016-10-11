
REM %1 = <aopj2ee_base>/aopj2ee
REM %2 = <aopj2ee>/ejbcomponents

echo server.name = %1
echo basedir = %2
ant -f %2\SunONEClient.xml distClientComponents -Dserver.name=%1 -Dbasedir=%2