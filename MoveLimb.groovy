import org.apache.commons.io.IOUtils;
import  com.neuronrobotics.bowlerstudio.physics.*;
import com.neuronrobotics.bowlerstudio.threed.*;

MobileBase base;
Object dev = DeviceManager.getSpecificDevice(MobileBase.class, "CarlTheWalkingRobot");
println "found: "+dev
//Check if the device already exists in the device Manager
if(args==null){
	if(dev==null){
		//Create the kinematics model from the xml file describing the D-H compliant parameters. 
		def file=["https://gist.github.com/bcb4760a449190206170.git","CarlTheRobot.xml"]as String[]
		String xmlContent = ScriptingEngine.codeFromGit(file[0],file[1])[0]
		MobileBase mb =new MobileBase(IOUtils.toInputStream(xmlContent, "UTF-8"))
		mb.setGitSelfSource(file)
		mb.connect()
		DeviceManager.addConnection(mb,mb.getScriptingName())
		base=mb
		println "Waiting for cad to generate"
		ThreadUtil.wait(1000)
		while(MobileBaseCadManager.get( base).getProcesIndictor().getProgress()<1){
			println "Waiting for cad to get to 1:"+MobileBaseCadManager.get( base).getProcesIndictor().getProgress()
			ThreadUtil.wait(1000)
		}
	}else{
		println "Robot found, runing code"
		//the device is already present on the system, load the one that exists.
	  	base=(MobileBase)dev
	}
}else
	base=(MobileBase)args.get(0)


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