import {Filter} from './filter'

export class Mapper {
    constructor(public from: string,
                public to: string,
                public def: string,
                public filter: Filter) {
    }
}