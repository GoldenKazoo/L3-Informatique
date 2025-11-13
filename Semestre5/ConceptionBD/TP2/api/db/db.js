const Database = require('better-sqlite3');
const fs = require('fs');
const path = require('path');

const dbPath = path.resolve('./db/db.sqlite');
const shPath = path.resolve('./db/schema.sql');

function db_init() {
    let recreate = false;
    if (!fs.existsSync(dbPath)) {
        recreate = true;
    } else {
        const dbStat = fs.statSync(dbPath);
        const schemaStat = fs.statSync(shPath);
        if (schemaStat.mtime > dbStat.mtime) {
            fs.unlinkSync(dbPath);
            recreate = true;
        }
    }
    const db_ = new Database(dbPath);
    db_.pragma('foreign_keys = ON');
    db_.pragma('recursive_triggers = ON');
    if (recreate) {
        const schema = fs.readFileSync(shPath, 'utf8');
        db_.exec(schema);
    }
    return db_;
}

const db = db_init();

module.exports = db ;
