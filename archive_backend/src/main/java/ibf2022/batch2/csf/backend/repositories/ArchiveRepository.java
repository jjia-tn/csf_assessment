package ibf2022.batch2.csf.backend.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.Utils;
import ibf2022.batch2.csf.backend.models.Archive;
// import jakarta.json.JsonObject;

@Repository
public class ArchiveRepository {

	public static final String C_ARCHIVES = "archives";

	@Autowired
	private MongoTemplate mongoTemplate;

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	// public Object recordBundle(/* any number of paramaters here */) {
	// 	return null;
	// }

	/*
     	* db.archives.insertOne({
     	*  bundleId: 'abcd1234',
		*  date: 'date'
     	*  title: 'title',
		*  name: 'name',
		*  urls: [{
		*			'url1',
		*			'url2',
		*			'url3'
		*		 }]
     	* })
    */
	public String recordBundle(String name, String title, String comments, String url) {

		String bundleId = UUID.randomUUID().toString().substring(0, 8);

		Document doc = new Document();

		doc.put("bundleId", bundleId);
		doc.put("date", new Date().toString());
		doc.put("title", title);
		doc.put("name", name);
		doc.put("comments", comments);
		doc.put("url", url);
		
        // Document toInsert = Document.parse(doc.toString());

        mongoTemplate.insert(doc, C_ARCHIVES);

		return bundleId;
    }

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	// public Object getBundleByBundleId(/* any number of parameters here */) {
	// 	return null;
	// }

	/* db.archives.find({ bundleId: 'abcd1234' }) */
	public List<Archive> getBundleByBundleId(String bundleId) {

		Criteria criteria = Criteria.where("bundleId").is(bundleId);
	
		Query query = Query.query(criteria);
	
		query.fields()
			.include("bundleId", "date", "title", "name", "comments", "url")
			.exclude("_id");

		return mongoTemplate.find(query, Document.class, C_ARCHIVES)
			.stream()
			.map(Utils::toJson)
			.map(Utils::toArchive)
			.toList();
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	// public Object getBundles(/* any number of parameters here */) {
	// 	return null;
	// }

	/* db.archives.find({ _id: 0, title: 1, date: 1 })
    *	.sort({ date: -1, title: 1 }) */
	public List<Archive> getBundles() {

        // Query query = Query.query()
        //     .with(Sort.by(Direction.ASC, "title")
		// 	.with(Sort.by(Direction.DESC, "date")));

        // query.fields()
        //     .include("title", "date")
        //     .exclude("_id");
		
		return mongoTemplate.findAll(Document.class, C_ARCHIVES)
			.stream()
			.map(Utils::toJson)
			.map(Utils::toArchive)
			.toList();
	}


}
