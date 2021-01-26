module.exports = function makeExpressCallback(controller){
    return (req,res)=>{
        const httpRequest = {
            params:req.params,
            body:req.body,
            id:req.body.id
        }

        controller(httpRequest)
            .then((httpResponse)=>{

                if (httpResponse.headers) {
                    res.set(httpResponse.headers)
                }
                res.type('json');
                res.status(httpResponse.statusCode).send(httpResponse.body);
           
            }).catch(e=>{
                res.status(500).send("Unknown error occurred.");
            })
    }
}