package test.util;

import java.io.File;
import java.util.Optional;

class Computer {
	private Optional<Soundcard> soundcard = Optional.empty();

	public Optional<Soundcard> getSoundcard() {
		return soundcard;
	}

	public Computer(Soundcard soundcard) {
		super();
		this.soundcard = Optional.ofNullable(soundcard);
	}

	public Computer() {
		super();
	}

}

class Soundcard {
	public Soundcard(USB usb) {
		super();
		this.usb = Optional.ofNullable(usb);
	}

	private Optional<USB> usb = Optional.empty();

	public Optional<USB> getUSB() {
		return usb;
	}

}

class USB {
	private String version = null;

	public USB(String version) {
		super();
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "USB [version=" + version + "]";
	}
}

public class OptionalExample {

	
	private static void processUSB(USB usb) {
		System.out.println(usb);
	}
	
	public static void main(String[] args) {
		
		
		Optional<USB> maybeUSB = Optional.of(new USB("3.0"));
		maybeUSB.filter(usb -> "3.0".equals(usb.getVersion())).ifPresent(OptionalExample::processUSB);
		
	
		Optional<Computer> computer1 = Optional.of(new Computer());
		String version1 = computer1.flatMap(Computer::getSoundcard)
                .flatMap(Soundcard::getUSB)
                .map(USB::getVersion)
                .orElse("UNKNOWN");
		
		System.out.println(version1);
		
		Optional<Computer> computer2 = Optional.of(new Computer(new Soundcard(new USB("1.0"))));
		String version2 = computer2.flatMap(Computer::getSoundcard)
                .flatMap(Soundcard::getUSB)
                .map(USB::getVersion)
                .orElse("UNKNOWN");
		
		System.out.println(version2);
		
		Optional<Computer> computer3 = Optional.ofNullable(null);
		String version3 = computer3.flatMap(Computer::getSoundcard)
                .flatMap(Soundcard::getUSB)
                .map(USB::getVersion)
                .orElse("UNKNOWN");
		
		System.out.println(version3);
		
		Optional<File> file = Optional.ofNullable(null);
		Optional<String> path = file.map(f -> f.getAbsoluteFile()).map(File::getAbsolutePath);
		
		System.out.println(path);
	}
}
