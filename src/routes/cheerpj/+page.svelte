<script>
import { onMount } from 'svelte';

// CheerpJ runtime loader. Pinned version for reproducible builds.
const CHEERPJ_LOADER = "https://cjrtnc.leaningtech.com/4.3/loader.js";
// The demo jar is served from the site root; CheerpJ's /app/ maps to that root.
const DEMO_JAR = "/app/cheerpj-demo/HelloCheerpJ.jar";

let status = "Loading CheerpJ runtime...";
let failed = false;

function loadScript(src) {
	return new Promise((resolve, reject) => {
		const s = document.createElement("script");
		s.src = src;
		// Required so the cross-origin runtime loads under COOP/COEP isolation.
		s.crossOrigin = "anonymous";
		s.onload = resolve;
		s.onerror = () => reject(new Error("Failed to load " + src));
		document.head.appendChild(s);
	});
}

async function boot() {
	try {
		await loadScript(CHEERPJ_LOADER);
		status = "Initializing JVM...";
		// eslint-disable-next-line no-undef
		await cheerpjInit();
		const display = document.getElementById("cheerpjDisplay");
		// eslint-disable-next-line no-undef
		cheerpjCreateDisplay(-1, -1, display);
		status = "Running HelloCheerpJ.jar...";
		// eslint-disable-next-line no-undef
		await cheerpjRunJar(DEMO_JAR);
		status = "Java application running.";
	} catch (e) {
		failed = true;
		status = "CheerpJ failed to start: " + e.toString();
		console.error(e);
	}
}

onMount(boot);
</script>

<svelte:head>
	<title>CheerpJ Test - Java in the browser</title>
</svelte:head>

<main class="w-full h-full flex flex-col bg-neutral-900 text-neutral-100">
	<header class="flex items-center gap-3 px-4 py-2 border-b border-neutral-700 shrink-0">
		<span class="font-bold">CheerpJ Test</span>
		<span class="text-sm {failed ? 'text-red-400' : 'text-neutral-400'}">{status}</span>
		<a class="ml-auto text-sm underline text-neutral-300" href="/" target="_blank">Back to WebVM (CheerpX)</a>
	</header>
	<div id="cheerpjDisplay" class="flex-1 min-h-0"></div>
</main>
