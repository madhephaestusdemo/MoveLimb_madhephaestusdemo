import org.apache.commons.io.IOUtils;
import  com.neuronrobotics.bowlerstudio.physics.*;
import com.neuronrobotics.bowlerstudio.threed.*;
def base;
//Check if the device already exists in the device Manager
if(args==null){
	base=DeviceManager.getSpecificDevice( "MediumKat",{
			return ScriptingEngine.gitScriptRun(	"https://github.com/OperationSmallKat/SmallKat_V2.git", 
											"loadRobot.groovy", 
											[ "https://github.com/OperationSmallKat/greycat.git",
											  "MediumKat.xml",
											  "GameController_22"]
			  )
		})
}else
	base=args.get(0)



println "Now we will move just one leg"
DHParameterKinematics leg0 = base.getAllDHChains().get(1)
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