package org.zerock.springex.sample;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
//@Qualifier("normal")
public class SampleDAOImpl implements SampleDAO {

}
