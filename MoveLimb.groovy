import org.apache.commons.io.IOUtils;
import  com.neuronrobotics.bowlerstudio.physics.*;
import com.neuronrobotics.bowlerstudio.threed.*;

def base;
//Check if the device already exists in the device Manager
if(args==null){
	base=DeviceManager.getSpecificDevice( "CarlTheWalkingRobot",{
			//If the device does not exist, prompt for the connection
			
			MobileBase m = MobileBaseLoader.fromGit(
				"https://github.com/madhephaestus/carl-the-hexapod.git",
				"CarlTheRobot.xml"
				)
			if(m==null)
				throw new RuntimeException("Arm failed to assemble itself")
			println "Connecting new device robot arm "+m
			return m
		})
}else
	base=args.get(0)


println "Now we will move just one leg"
DHParameterKinematics leg0 = base.getAllDHChains().get(0)
double zLift=25
println "Start from where the arm already is and move it from there with absolute location"
TransformNR current = leg0.getCurrentPoseTarget();
current.translateZ(zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)// wait for the legs to fully arrive

println "and reset it"
current.translateZ(-zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)// wait for the legs to fully arrive

current.translateX(zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)
println "wait for the legs to fully arrive"
println "and reset it"
current.translateX(-zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)
println " wait for the legs to fully arrive"

current.translateY(-zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)
println " wait for the legs to fully arrive"
println "and reset it"
current.translateY(zLift);
leg0.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)
println " wait for the legs to fully arrive"

return null;