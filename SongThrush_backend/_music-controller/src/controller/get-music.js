module.exports = function makeGetMusic({readMusic}){
   
    return async function getMusic(httpRequest){
        
        const id = httpRequest.id;

        try {
            const readed = await readMusic(id);
        
            return {
                headers: {
                    'Content-Type': 'application/json',
                },
                statusCode: 200,
                body: readed
            }            
        
        } catch (e) {
        
            return {
                headers: {
                    'Content-Type': 'application/json',
                },
                statusCode: 400,
                body: e.message
            }
        
        }

    }
}