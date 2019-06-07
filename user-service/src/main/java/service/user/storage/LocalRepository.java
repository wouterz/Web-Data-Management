package service.user.storage;

import org.springframework.stereotype.Repository;

import service.user.models.StockItem;
import service.user.models.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocalRepository implements Dao {

	private List<StockItem> StockItems = new ArrayList<>();

	@Override
	public String create(long id) {
		StockItem stockItem = new StockItem(id, "emptyName");
		StockItems.add(stockItem);
		//return id;
		return "no";
	}

	@Override
	public Object update(long id, Object stockItem) {
		StockItems.set((int) id, (StockItem) stockItem);
		return stockItem;
	}

	@Override
	public StockItem get(long id) {
		List<Long> ids = new ArrayList<>(1);
		ids.add(id);
		return StockItems.stream().filter(p -> ids.contains(p.getId())).findFirst().orElse(null);
	}

	@Override
	public boolean delete(long id) {
		StockItem stockItem = StockItems.remove((int) id);
		return stockItem.getId() != 0;
	}

}
