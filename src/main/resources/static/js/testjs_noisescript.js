let offset1 = 0;
let offset2 = 10000;

function setup() {
	let canvas = createCanvas(400, 400);
	canvas.parent('canvas-holder');

	colorMode(HSB, 360, 100, 100, 1);
	setTimeout(stopAndSave, 7777);

	// background(0, 100, 10, 1);
}

function stopAndSave()
{
	noLoop();
	save("testImage.png");
}

function draw() {
	background(0, 0, 30, 1);

	// var x = random(width);
	let x = map(noise(offset1), 0, 1, 0, width);
	let y = map(noise(offset2), 0, 1, 0, height);

	offset1 += 0.02;
	offset2 += 0.02;

	ellipse(x, y, 24, 24);

}
