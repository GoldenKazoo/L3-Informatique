async function client_run(url) {
  try {
    const res = await fetch(url, {
      method: 'POST',
    });
    console.log({
      run: `${res.status} (${res.statusText})`,
    });
  } catch (err) {
    console.error("client_run error:", err);
    throw err;
  }
}

async function client_read(url) {
  try {
    const res = await fetch(url, {
      method: 'GET',
    });
    const body = await res.text();
    const contentType = await res.headers.get('Content-Type');
    console.log({
      read: `${res.status} (${res.statusText})`,
      body: body && contentType.includes('application/json') ? JSON.parse(body) : null
    });
  } catch (err) {
    console.error("client_read error:", err);
    throw err;
  }
}

async function client_create(url, data) {
  try {
    const res = await fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });
    console.log({
      create: `${res.status} (${res.statusText})`,
      location: res.headers.get('Location')
    });
  } catch (err) {
    console.error("client_update error:", err);
    throw err;
  }
}

async function client_update(url, data) {
  try {
    const res = await fetch(url, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });
    console.log({
      update: `${res.status} (${res.statusText})`,
      location: res.headers.get('Location')
    });
  } catch (err) {
    console.error("client_update error:", err);
    throw err;
  }
}

async function client_delete(url) {
  try {
    const res = await fetch(url, {
      method: 'DELETE',
    });
    console.log({
      delete: `${res.status} (${res.statusText})`,
    });
  } catch (err) {
    console.error("client_update error:", err);
    throw err;
  }
}




