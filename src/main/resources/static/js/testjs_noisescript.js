var offset1 = 0;
var offset2 = 10000;

function setup() {
	var canvas = createCanvas(400, 400);
	canvas.parent('canvas-holder');

	colorMode(HSB, 360, 100, 100, 1);
	background(0, 100, 10, 1);
}

function draw() {
	background(0, 0, 30, 1);

	// var x = random(width);
	var x = map(noise(offset1), 0, 1, 0, width);
	var y = map(noise(offset2), 0, 1, 0, height);

	offset1 += 0.02;
	offset2 += 0.02;

	ellipse(x, y, 24, 24);

}
