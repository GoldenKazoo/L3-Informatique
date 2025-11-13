// ////////////////////////////////
// Server HTTP
const path = require('path');
const express = require('express');
const app = express();
const PORT = 8887;

// Type: application/json for body + access control
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Routes
app.get('/', (req, res) => res.json({
  _links: [
    { rel: 'personnes', href: '/personnes/' },
    { rel: 'médecins', href: '/medecins/' },
    { rel: 'patients', href: '/patients/' },
    { rel: 'spécialités', href: '/specialites/' },
    { rel: 'spécialistes', href: '/specialistes/' },
    { rel: 'suivis', href: '/suivis/' },
    { rel: 'actes', href: '/actes/' },
    { rel: 'interventions', href: '/interventions/' },
    { rel: 'consultations', href: '/consultations/' },
  ]
}));
app.get('/index.html', (req, res) => res.sendFile(path.join(__dirname, 'public/index.html')));
app.get('/client.js', (req, res) => res.sendFile(path.join(__dirname, 'public/client.js')));
app.get('/favicon.ico', (req, res) => res.status(204).end());

// Routes
app.use('/patients', require('./routes/patient'));
app.use('/medecins', require('./routes/medecin'));
app.use('/specialites', require('./routes/specialite'));
// Run API
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`)
});


