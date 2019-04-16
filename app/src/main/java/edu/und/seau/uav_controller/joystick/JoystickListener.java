package edu.und.seau.uav_controller.joystick;

public interface JoystickListener {
    void onJoystickMoved(float xPercent, float yPercent, int id);
}
