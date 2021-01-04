const mongoose = require('mongoose');
const express = require('express');
const methodOverride = require('method-override');
const connectionRoutes = require('./routes/connectionRoutes');
const userRoutes = require('./routes/userRoutes');
const rsvpRoutes = require('./routes/rsvpRoutes');

const session = require('express-session');
const flash = require('connect-flash');

const app = express();

// register view engine
app.set('view engine', 'ejs');
app.use('/public',express.static('public'));
app.use(express.urlencoded({ extended: true }));

mongoose.connect('mongodb://localhost:27017/exerciseDB', { useUnifiedTopology: true, useNewUrlParser: true, useFindAndModify: false, useCreateIndex: true })
    .then((result) => app.listen(8084))
    .catch((err) => console.log(error));

app.use(session({
    secret: 'NBDA',
    resave: false,
    saveUninitialized: false,
}));

app.use(flash());

app.use(methodOverride('_method'));

app.use((req, res, next) => {
    res.locals.user = req.session.user || null;
    res.locals.errorMessages= req.flash('error');
    res.locals.successMessages= req.flash('success');
    res.locals.messages = req.flash();
    next();
});

// index page
app.get('/', (req, res) => {      
    res.render('index');
});

app.use('/events', connectionRoutes);
app.use('/users', userRoutes);
app.use('/rsvp', rsvpRoutes);


//aboutus page
app.get('/aboutus', (req, res) => {      
    res.render('aboutus');
});

//contact us page
app.get('/contactus', (req, res) => {      
    res.render('contactus');
});

// 404 page
app.use((req, res) => {  
    res.status(404).render('error',{message:'Page cannot be found on the server'});
});