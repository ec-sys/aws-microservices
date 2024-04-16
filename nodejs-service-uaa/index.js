const express = require('express')
const app = express()
const port = 9001

app.get('/uaa', (req, res) => {
    res.send('Hello World!')
})

app.get('/uaa/user-mng', (req, res) => {
    res.send('Hello User Mamagement!')
})

app.get('/uaa/actuator/health', (req, res) => {
    res.send('UP')
})

app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
})