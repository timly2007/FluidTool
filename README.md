# FluidTool
A interactive tool to visualize pressure and velocity of a fluid surrounding an object with constant velocity.
===============================================================================================================
This tool is an updated version (7/31/24) of a JavaFX Desktop Application I built for the purpose of visualizing air flow around an object. 
Here are the major functions:

- Real-Time Particle Based Simulation (1000+ particles)
- Colored particles to indicate velocity as well as a button to show direction
- Custom Objects (created by dragging while holding on your cursor)
- Air Speed
- Radius of Particles (visual only)
- Wall Shear Stress
- Velocity Gradient Generator (accuracy can be increased by lowering variable "pixelSize" and increasing "totalFrames" at the cost of larger time)

NOTE: To increase performance, the maximum amount of objects has been capped at 8. If you would like to increase the total amount of objects the program 
can handle, please modify the "totalObjects" variable.
