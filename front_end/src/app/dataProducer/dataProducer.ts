import {Filter} from './filter'
import {Mapper} from './mapper'

export class DataProducer {
    constructor(public name: string,
                public filters: Filter[],
                public source: string,
                public mappers: Mapper[]) {
    }
}