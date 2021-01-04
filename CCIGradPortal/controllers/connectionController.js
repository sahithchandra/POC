const Connection = require('../models/connection');
const Rsvp = require('../models/rsvp');
const validationResult= require('express-validator').validationResult;

exports.getAllConnections = (req, res,next) => {
    let map= new Map();
    Connection.find().then(connections => {
        for(let i=0;i<connections.length;i++){
            let events=[];
            let topic=connections[i].connectionTopic;
            if(!map.has(topic)){       
                events.push(connections[i]);
                map.set(topic,events);    
            }        
            else{
                let array=map.get(topic);
                array.push(connections[i])
                map.set(topic,array);
            }         
        }
        res.render('./connections/connections',{map:map});
    })
    .catch(err => {
        console.log(err);
        next();
    });   
}

exports.getSavedConnections = (req, res,next) => { 
    Promise.all([Rsvp.find({user:req.session.user.id}).populate('connection'), 
            Connection.find({user:req.session.user.id})])           
        .then(result => {
                if (result){
                    const [rsvps,connections] = result;  
                    req.flash('success','Successfully Created or updated the RSVP for the event!');  
                    res.render('./connections/savedConnections', { rsvps,connections});
                    console.log(res.locals.successMessages);
                }
            else
                res.redirect('/connections');
        })
        .catch(err => {
            console.log(err);
            next();
    });   
}

exports.getConnectionDetail = (req, res, next) => {
    Connection.findById(req.params.id).populate('user','firstName')          
        .then(result => {           
            if (result){ 
                Rsvp.find({ connection:req.params.id,action:'Yes'}).countDocuments().then(count=>{
                    res.render('./connections/connection', { event: result,count:count});
                })            
               .catch(err=>{
                    res.render('./connections/connection', { event: result,count:0});
               });
            }
            else
                res.redirect('/connections');
        })
        .catch(err => {
            console.log(err);
            next();
        });
}

exports.saveConnection = (req, res, next) => {
    const errors= validationResult(req);
    if(errors.isEmpty()){ 
    let connection = new Connection({
        connectionName: req.body.connectionName,
        connectionTopic: req.body.connectionTopic,
        details: req.body.details,
        date: req.body.date,
        startingTime: req.body.startingTime,
        endingTime: req.body.endingTime,
        hostName: req.body.hostName,
        image: req.body.image,
        location: req.body.location,
        user:req.session.user.id
    });
    connection.save()
        .then(result => {
            res.redirect('/events/getAllConnections');
        })
        .catch(err => {
            console.log(err);
            next();
        });
    }
    else{
        errors.array().forEach((error)=>{
            req.flash('error',error.msg);
            })
        res.redirect('/events/createConnection');
    }

}

exports.createConnection = (req, res, next) => {
    res.render('./connections/createConnection');
}

exports.getConnectionUpdate = (req, res, next) => {
    Connection.findById(req.params.id)
        .then(result => {
            if (result && result.user.equals(req.session.user.id))
                res.render('./connections/updateConnection',{data:result});
            else
                res.redirect('/events/getAllConnections');
        })
        .catch(err => {
            console.log(err);
            next();
        });
}
exports.updateConnection = (req, res, next) => {
    const errors= validationResult(req);
    if(errors.isEmpty()){ 
    let connectionParams = {
        connectionName: req.body.connectionName,
        connectionTopic: req.body.connectionTopic,
        details: req.body.details,
        date: req.body.date,
        startingTime: req.body.startingTime,
        endingTime: req.body.endingTime,
        hostName: req.body.hostName,
        image: req.body.imageURL,
        location: req.body.location
    };
    Connection.findById(req.params.id)
        .then(result => {
            if (result && result.user == req.session.user.id)
                return Connection.findByIdAndUpdate(req.params.id, { $set: connectionParams });               
            else
                res.redirect('/events');
        })
       .then(result=>{
            res.redirect('/events/' + req.params.id);
       })
        .catch(err => {
            console.log(err);
            next();
        });
    }
        else{
            errors.array().forEach((error)=>{
                req.flash('error',error.msg);
                })
            res.redirect('/events/createConnection');
        }
}

exports.deleteConnection = (req, res, next) => {   
    Connection.findById(req.params.id)
    .then(result => {
        if (result && result.user == req.session.user.id)
            return Promise.all([Rsvp.findOneAndDelete({connection:req.params.id}), 
                Connection.findByIdAndDelete(req.params.id)]) ;               
        else
            res.redirect('/events');
    })
   .then(result=>{
        res.redirect('/events/getAllConnections');
   })
        .catch(err => {
            console.log(err);
            next();
        });
}

exports.authenticate = (req, res, next) => {
    if (!req.session.user) {
        res.redirect("./users/login");
    } else {
        next();
    }
}